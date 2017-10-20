# Shotbot 3.0

Web-based screenshot designer

### Staging

[![Build Status][build-master]][shotbot-travis]

On Skygear: [https://shotbotstaging.skygeario.com/static][]

### Production

[![Build Status][build-production]][shotbot-travis]

On S3: [https://app.shotbot.io][]

On Skygear: [https://shotbot.skygeario.com/static][]


## Quick Reference

Run these in the project root.

```
$ (rlwrap) lein dev       # clean, start local dev environment
$ lein hiera              # generate ns-graph
$ lein clean              # clean all artifacts
$ lein deploy-staging!    # clean, build, deploy to staging server
$ lein deploy-production! # clean, build, deploy to production server (does not deploy to S3)
```

## Documentation

Please read the project's docs [here][shotbot-doc].
Update the docs as nessesary in future development.

[shotbot-doc]: doc/
[shotbot-travis]: https://travis-ci.com/oursky/shotbot3
[build-master]: https://travis-ci.com/oursky/shotbot3.svg?token=KUhZJWtRphqCQF3xzmxM&branch=master
[build-production]: https://travis-ci.com/oursky/shotbot3.svg?token=KUhZJWtRphqCQF3xzmxM&branch=production
[https://shotbotstaging.skygeario.com/static]: https://shotbotstaging.skygeario.com/static
[https://shotbot.skygeario.com/static]: https://shotbot.skygeario.com/static
[https://app.shotbot.io]: https://app.shotbot.io
