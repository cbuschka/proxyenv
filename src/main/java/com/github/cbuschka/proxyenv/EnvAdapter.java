package com.github.cbuschka.proxyenv;

public interface EnvAdapter
{
	boolean handles();

	ProxyConfig extract();
}
