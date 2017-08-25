package com.github.cbuschka.proxyenv;

import java.net.URI;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ProxyConfig
{
	private HostWithPort httpProxyHost;
	private HostWithPort httpsProxyHost;
	private List<NonProxyHost> httpNonProxyHosts = new ArrayList<>();
	private HostWithPort ftpProxyHost;
	private List<NonProxyHost> ftpNonProxyHosts = new ArrayList<>();

	public ProxyConfig()
	{
		this(null, null, Collections.emptyList(), null, Collections.emptyList());
	}

	public ProxyConfig(HostWithPort httpProxyHost, HostWithPort httpsProxyHost, List<NonProxyHost> httpNonProxyHosts, HostWithPort ftpProxyHost, List<NonProxyHost> ftpNonProxyHosts)
	{
		this.httpProxyHost = httpProxyHost;
		this.httpsProxyHost = httpsProxyHost;
		this.httpNonProxyHosts.addAll(httpNonProxyHosts);
		this.ftpProxyHost = ftpProxyHost;
		this.ftpNonProxyHosts.addAll(ftpNonProxyHosts);
	}

	public HostWithPort getHttpProxyHost()
	{
		return httpProxyHost;
	}

	public void setHttpProxyHost(HostWithPort httpProxyHost)
	{
		this.httpProxyHost = httpProxyHost;
	}

	public HostWithPort getHttpsProxyHost()
	{
		return httpsProxyHost;
	}

	public void setHttpsProxyHost(HostWithPort httpsProxyHost)
	{
		this.httpsProxyHost = httpsProxyHost;
	}

	public HostWithPort getFtpProxyHost()
	{
		return ftpProxyHost;
	}

	public void setFtpProxyHost(HostWithPort ftpProxyHost)
	{
		this.ftpProxyHost = ftpProxyHost;
	}

	public List<NonProxyHost> getHttpNonProxyHosts()
	{
		return Collections.unmodifiableList(httpNonProxyHosts);
	}

	public void setHttpNonProxyHosts(List<NonProxyHost> httpNonProxyHosts)
	{
		this.httpNonProxyHosts.clear();
		this.httpNonProxyHosts.addAll(httpNonProxyHosts);
	}


	public List<NonProxyHost> getFtpNonProxyHosts()
	{
		return Collections.unmodifiableList(ftpNonProxyHosts);
	}

	public void setFtpNonProxyHosts(List<NonProxyHost> ftpNonProxyHosts)
	{
		this.ftpNonProxyHosts.clear();
		this.ftpNonProxyHosts.addAll(ftpNonProxyHosts);
	}
}
