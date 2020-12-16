# Design Documentation

Design documentation shows how some of the key components of this application works.

## Server client architecture

Feedback system is implemented using client server architecture. In addition to
Feedback service, system access Google OAuth 2.0 + OpenID service to provide an
alternative way to log in to the system. Feedback service and google
authorization service is based on HTTP protocol.

```
  +---------------+                        +---------------+
  |               |                        |               |
  |    Feedback   +<------get-and-set------+    Feedback   +<--------------------+
  |    Service    |       feedback         |    Client     |                     |
  |               |                        |               |                     |
  +------+--------+                        +-------+-------+                     |
         |                                         |                             |
         |                                         |                             |
         |                                         |                             |
         |                                         |                             |
Authorization code                                 |                             |
Access Token exchange                     User Authentication &         Authorization completion
         |                                Authorization                 client redirect
         |                                         |                             |
         |                                         |                             |
         |         +-------------------+           |                             |
         |         |                   |           |                             |
         +-------->+   Google OAuth2   +<----------+                             |
                   |   Service         |                                         |
                   |                   +-----------------------------------------+
                   +-------------------+
```

### Google OAuth2 (Google Sign In)

Google Sign In has different ways of authentication and authorization depends
of how and where it's being used. In this use case, "Backend server validation"
is used. Instead of handling all user within and
requiring them to register again in Feedback service, This is allowing users to
use their existing Google account to sign in to the current service as an
alternative way. Following is the workflow of the Feedback service authentication process.

NOTE: From Google Sign In server perspective, this is a Authorization process
Feedback service is the client. But from Feedback service perspective, it's
a Authentication process.

```
+--------------------------------------------------------+
|                                                        |
|                   Client PC                            |
|                                                        |
|                                                        |
| +-----------------+               +------------------+ |                 +-----------------+
| |                 |               |                  +-------(4)-------->+                 |
| |  Native Client  +------(3)----->+  Client Browser  | |                 |  Google OAuth2  |
| |                 |               |                  +<------(5)---------+                 |
| +-------+---------+               +--------+---------+ |                 +--------+--------+
|         |                                  |           |                          ^
|         |                                  |           |                          |
+--------------------------------------------------------+                          |
          |                                  |                                      |
          |                                  |                                      |
          |                                  |                                      |
          |                                 (6)                                     |
          |                                  |                                      |
          |                                  |                                      |
         (1)                                 |                                      |
          |    +-----------------------------+                                      |
          |    |                                                                    |
          |    |                                                                    |
          |    |                                                                    |
          |    |                                                                    |
          |    |                                                                    |
          v    v                                                                    |
       +--+----+--+                                                                 |
       |          |                                                                 |
       |  Server  +--------------------------------(7)------------------------------+
       |          |
       +----+-----+
            |
            |
           (2)
            |
            v
  +---------+---------+
  |                   |
  |  Session Details  |
  |                   |
  +-------------------+
```

1. Native client make the initial request to get a session.
   - All clients need to have a session in order to interact with the service
     regardless of the authentication status. (authenticated or not).
   - If any client failed to provide its session at Social login request will
     be rejected
1. Spring service creates and sets the cookie in client. (sends SetCookie header)
1. Native client creates a thread (to not block the main UI controller
   thread) and opens a browser window with get request to "/login/social"
   endpoint.
   - Client should send the type of social login (Google or Facebook or other.
     Feedback service support only Google social logins at the moment)
   - Based on the requested social login, Feedback service sends a client side
     redirect response back to browser to navigate Social authorization
     application. (For Google it's https://accounts.google.com/o/oauth2/auth)
   - Authentication thread will wait for 60 seconds until user is
     authenticated. In case of timeout, social login will be canceled and user
     will be returned back to login.
1. User authorize Feedback service to use his or her email address. (Scope of
   the authorization is openid and email)
1. Once the authorization is done, Google sign in service sends redirect call
   back to the client browser. Redirect location is the one configured in Google
   APIs project for Feedback service.
   - Feedback client sends the ClientID of Feedback credentials, and that's how Google service
     identifies the service that the client is trying to authorize.
1. Client browser sends authorization code to Feedback service
1. Feedback service sends authorization code to google service to make sure it's
   valid and to exchange authorization code to access token.
   - Alongside access token, Google service sends email and isVerified details
   - If user is not verified, login process will be terminated.
   - If verified, current Feedback session is marked as authenticated with the
     returned email address.

## Database

Server uses JPA for data access layer allowing developers to perform CRUD
operations without writing queries. With ORM, developer can have seamless
database integration. Spring JPA features makes very easy to handle database
since it's handling most of the boilerplate code.

To share the entities between server and client, a separate project is used.

```
                                                                        +----------+
                                                                        |          |
                                                       +--------------->+  Seller  |
                                                       |                |          |
                                                 +-----+-----+          +----------+
                                                 |           |
                               +---------------->+  Product  |
                               |                 |           |
+------------+          +------+------+          +-----------+
|            |          |             |
|  Customer  +--------->+   Purchase  |
|            |          |   History   |
+------------+          |             |
                        +------+------+          +------------+
                               |                 |            |
                               +---------------->+  Feedback  |
                                                 |            |
                                                 +-----+------+
                                                       |
                                                       |
                                                       |                +------------+              +--------------+
                                                       |                |            |              |              |
                                                       +--------------->+  Feedback  +------------->+   Questions  |
                                                                        |            |              |              |
                                                                        +------------+              +--------------+
```

## Feedback Native Client UI

Javafx has an alternative way of creating UIs without doing it
programmatically using FXML. FXML is a lot cleaner because you have the separation
between view and the controller. A lot of languages tend to use similar
technologies for UI. Every view has it's own controller. Controllers that
doesn't need special construction are directly referenced in fxml view.
FXMLLoader is responsible for initializing the controller.

```
fx:controller="org.s1n7ax.feedback.controller.LoginController"
```

In case of an controller that has overridden constructors, they are initialized
and assigned programmatically.

```
FeedbackRecordController ctrl = new FeedbackRecordController(id, rate, question, onRateChange);
return new DefaultFXMLParentFactory().getParent(FXMLConfiguration.FEEDBACK_RECORD_VIEW_PATH, ctrl);
```

## Feedback Native Client Requests

Feedback native client has encapsulated the business logs so it's doesn't matter
how the server connection is handled but the default implementation uses apache
HTTP client to send requests. Apache http client has global client (singleton)
that provide access to http client object. In `DefaultApacheHttpClientService`
object construction, it also creates a `CookieStore` associated with the client.
So all the incoming `Set-Cookie` headers are extracted and added to cookies
store and cookies that is not expired and relevant to the request domain will be
sent to the server automatically.

``` java
cookieStore = new BasicCookieStore();
client = HttpClientBuilder.create().setDefaultCookieStore(cookieStore).build();
```

Controllers should not access services directly. Instead controllers should use
`FeedbackService` business logics.

```
+----------+     +----------------+     +----------------------+
|          |     |                |     |                      |
|   View   +---->+   Controller   +---->+   Feedback Service   +-----+
|          |     |                |     |                      |     |
+----------+     +----------------+     +----------------------+     |
                                                                     |
                                                                     |
                                                                     |
                                                                     v
                                                       +-------------+-------------+
                                                       |                           |
                                                       |   Http Feedback Service   |
                                                       |                           |
                                                       +-------------+-------------+
                                                                     |
                                                                     |
                                                                     |
                        +--------------+                             |
                        |              |                             |
                        |    Service   +<----------------------------+
                        |              |
                        +--------------+
```

## Feedback Native Client Social Login

Social login entry point is in login view. On social login,
`SocialLoginLoadingScreenController` creates a new thread and start a background
service to check if the user social login is completed. Social login will be
timeout after 60 seconds. User can cancel the social login at any given point
and navigate back to login view.
