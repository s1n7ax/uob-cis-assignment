#!/bin/python

import urllib.parse

host = 'https://accounts.google.com/o/oauth2/v2/auth?'
param = {
    'redirect_uri': 'http://localhost:3000/auth',
    #  'prompt': 'consent',
    'response_type': 'code',
    'client_id': '113291176157-aibhsqjf655ve4f9ftueb9vfbb71u40h.apps.googleusercontent.com',
    'scope': 'openid email',
    #  'access_type': 'offline'
}

print(host + urllib.parse.urlencode(param))


# https://accounts.google.com/o/oauth2/v2/auth?redirect_uri=http://localhost:8080/oauth2&prompt=consent&response_type=code&client_id=113291176157-aibhsqjf655ve4f9ftueb9vfbb71u40h.apps.googleusercontent.com&scope=email&access_type=offline
