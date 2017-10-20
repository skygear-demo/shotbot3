
## skygear config

A custom S3 asset store is used because we need CORS-enabled images for canvas export.
The AWS user `shotbot` is granted access to the `app.shotbot.io-asset` S3 bucket.

```
ASSET_STORE             : s3
ASSET_STORE_REGION      : us-east-1
ASSET_STORE_BUCKET      : app.shotbot.io-asset
ASSET_STORE_ACCESS_KEY  : [Masked]
ASSET_STORE_SECRET_KEY  : [Masked]
```

Forget password plugin configuration (see plugin section below).

```
FORGOT_PASSWORD_SENDER      : admin@shotbot.io
FORGOT_PASSWORD_SUBJECT     : Reset your Shotbot account password
FORGOT_PASSWORD_URL_PREFIX  : http://app.shotbot.io
```

## record: `app`
```js
{
  _recordType: 'app',
  name: 'app name',
  template-id: 'template-1',
  shot-order: ['', '', '', '', '']            // array of shot record IDs in left to right order
  thumbnail: 'data:image//jpeg;base64,...',   // data URL
  export-zip: {url: 'http://...'}             // Skygear Asset (export zip fallback, only used when filesaver.js doesn't work.)
}
```

## record: `shot`
```js
{
  _recordType: 'shot',
  app-id: '8fca5d6d-0fae-4b90-bd7a-15e6419c2c64',
  template-index: 0                       // index of this shot inside the template
  data: {title: 'foo', subtitle: 'bar'},  // JSON object (see rendering doc)
  screen: {url: 'http://...'},            // Skygear Asset (uploaded screenshot)
  screen2: {url: 'http://...'},           // Skygear Asset (uploaded screenshot)
}
```

## record: `android_beta`
```js
{
  _recordType: 'android_beta',
  email: 'foo@bar.com'                     // Email address for Android beta
}
```

## lambda: `asset-proxy`

Fetches a skygear asset and returns the content as a data-url.

- args: `[assetURL]`
- return: `{assetURL: "", dataURL: ""}`

## plugin: `forgot_password`

This is a built-in plugin from Skygear cloud.
[https://github.com/SkygearIO/forgot_password](https://github.com/SkygearIO/forgot_password)

2 lambdas are used:

- `user:forgot-password [email]` sents a reset link to the requested email.
- `user:reset-password [user-id code new-password]` resets the user's password.
