# uilib-almuwahhid

# HTTP Request
# Using POST Request
```
UiLibRequest.POST(<your_address_here>, this, new UiLibRequest.OnPostRequest() {
            @Override
            public void onPreExecuted() {
                
            }

            @Override
            public void onSuccess(JSONObject response) {

            }

            @Override
            public void onFailure(String error) {

            }

            @Override
            public Map<String, String> requestParam() {
                return null;
            }

            @Override
            public Map<String, String> requestHeaders() {
                return null;
            }
        });
```

# Using GET Request
```
UiLibRequest.GET(<your_address_here>, this, new UiLibRequest.OnGetRequest() {
            @Override
            public void onPreExecuted() {
                
            }

            @Override
            public void onSuccess(JSONObject response) {

            }

            @Override
            public void onFailure(String error) {

            }

            @Override
            public Map<String, String> requestParam() {
                return null;
            }

            @Override
            public Map<String, String> requestHeaders() {
                return null;
            }
        });
```
