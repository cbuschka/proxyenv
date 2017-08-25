package com.github.cbuschka.proxyenv;

import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import java.util.regex.Pattern;

public class OsDetector
{
	private static List<Pattern> unixoidPatterns = Arrays.asList(Pattern.compile("^.*mac.*os.*x.*$"), Pattern.compile("^.*nix.*$"), Pattern.compile("^.*bsd.*$"), Pattern.compile("^.*nu.*x$"), Pattern.compile("^.*aix.*$"), Pattern.compile("^.*sunos$"));

	private Function<String, String> sysPropsAccessor;

	public OsDetector()
	{
		this((s) -> System.getProperty(s));
	}

	public OsDetector(Function<String, String> sysPropsAccessor)
	{
		this.sysPropsAccessor = sysPropsAccessor;
	}

	public boolean isUnixoid()
	{
		String osName = this.sysPropsAccessor.apply("os.name").toLowerCase();
		for (Pattern pattern : unixoidPatterns)
		{
			if (pattern.matcher(osName).matches())
			{
				return true;
			}
		}

		return false;
	}
}
