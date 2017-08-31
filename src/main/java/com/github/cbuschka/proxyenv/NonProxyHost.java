package com.github.cbuschka.proxyenv;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

public class NonProxyHost
{
	private static final Pattern IP4_ADDRESS_PATTERN = Pattern.compile("[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}");

	private static final List<Pattern> LOCALHOST_VALUES = Arrays.asList(Pattern.compile("^localhost$"), Pattern.compile("^127\\..*$"), Pattern.compile("^\\:\\:1$"));

	private String value;


	public static NonProxyHost valueOf(String value)
	{
		return new NonProxyHost(value);
	}

	public NonProxyHost(String value)
	{
		this.value = value.trim();
	}

	@Override
	public int hashCode()
	{
		return this.value.hashCode();
	}

	public boolean isLocalhost()
	{
		for (Pattern p : LOCALHOST_VALUES)
		{
			if (p.matcher(this.value).matches())
			{
				return true;
			}
		}

		return false;
	}

	@Override
	public boolean equals(Object o)
	{
		if (this == o) return true;

		if (o == null || getClass() != o.getClass()) return false;

		NonProxyHost that = (NonProxyHost) o;

		return value.equals(that.value);
	}

	public String getValue()
	{
		return value;
	}

	@Override
	public String toString()
	{
		return getClass().getSimpleName() + "{value=" + this.value + "}";
	}

	public boolean isIp()
	{
		return IP4_ADDRESS_PATTERN.matcher(this.value).matches();
	}
}

