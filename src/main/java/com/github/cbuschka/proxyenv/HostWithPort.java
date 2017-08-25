package com.github.cbuschka.proxyenv;

public class HostWithPort
{
	private String hostName;

	private int port;

	public HostWithPort(String hostName, int port)
	{
		this.hostName = hostName;
		this.port = port;
	}

	public String getHostName()
	{
		return hostName;
	}

	public int getPort()
	{
		return port;
	}

	public String toExternalForm()
	{
		return this.hostName + ":" + this.port;
	}

	@Override
	public int hashCode()
	{
		return toExternalForm().hashCode();
	}

	@Override
	public boolean equals(Object otherObj)
	{
		if (null == otherObj)
		{
			return false;
		}

		if (this == otherObj)
		{
			return true;
		}

		if (!getClass().equals(otherObj.getClass()))
		{
			return false;
		}

		HostWithPort other = (HostWithPort) otherObj;

		return this.hostName.equals(other.hostName) && this.port == other.port;
	}

	@Override
	public String toString()
	{
		return toExternalForm();
	}
}
