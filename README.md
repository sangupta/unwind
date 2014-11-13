unwind
======

`unwind` is a very simple Java library based on Apache `HTTP Client` library that given a short URL unwinds it
to the final destination. This is preferable in conditions when you are crawling content from 3rd party sources
and would like to show the actual link someone is pointing to.

For more information on the project, refer to https://github.com/sangupta/unwind project.

Features
--------

* Provides methods to indicate if a URL is shortened by any of the major URL shorteners
* Provides methods to indicate if a URL has redirects
* Provides methods to find the final destination given a URL

Usage
-----

Using the library is as simple as:

```java
UnwindService service = new HttpUnwindServiceImpl();

// check if this is a major URL shortener
// returns true
boolean result = service.isMajorShortener("http://goo.gl/LjAG");

// returns false
result = service.isMajorShortener("http://google.com");

// returns true
result = service.isRedirectedURL("http://github.com");

// for final destination URL
// returns https://www.facebook.com/
String finalURL = service.getFinalURL("http://facebook.com");
```

Downloads
---------

The library can be downloaded from Maven Central using:

```xml
<dependency>
    <groupId>com.sangupta</groupId>
    <artifactId>unwind</artifactId>
    <version>1.0.0</version>
</dependency>
```

Changelog
---------

**1.0.0**

* Initial release

Versioning
----------

For transparency and insight into our release cycle, and for striving to maintain backward compatibility, 
`unwind` will be maintained under the Semantic Versioning guidelines as much as possible.

Releases will be numbered with the follow format:

	<major>.<minor>.<patch>

And constructed with the following guidelines:

* Breaking backward compatibility bumps the major
* New additions without breaking backward compatibility bumps the minor
* Bug fixes and misc changes bump the patch

For more information on SemVer, please visit http://semver.org/.

License
-------
	
```
unwind - expand shortened urls
Copyright (c) 2014, Sandeep Gupta

	http://sangupta.com/projects/unwind

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

	http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```
