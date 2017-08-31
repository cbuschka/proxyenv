# proxyenv - A java tool to work with proxy env configs. [![Build Status](https://travis-ci.org/cbuschka/proxyenv.svg?branch=master)](https://travis-ci.org/cbuschka/proxyenv)

### Get the current proxy config
```
ProyConfig proxyConfig = new ProxyEnv().getProxyConfig();
```

### Convert proxy config to java opts
```
String javaOpts = new JavaOptsBuilder().withProxyConfig(proxyConfig).buildString();
```

## Author
Written by [Cornelius Buschka](https://github.com/cbuschka).

## License

[GPL-3.0](LICENSE)

