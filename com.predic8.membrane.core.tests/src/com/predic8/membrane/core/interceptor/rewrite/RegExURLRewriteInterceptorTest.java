/* Copyright 2011 predic8 GmbH, www.predic8.com

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

   http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License. */
package com.predic8.membrane.core.interceptor.rewrite;

import static org.junit.Assert.assertEquals;

import java.util.*;

import org.junit.Before;
import org.junit.Test;

import com.predic8.membrane.core.exchange.Exchange;
import com.predic8.membrane.core.interceptor.Outcome;
import com.predic8.membrane.core.interceptor.rewrite.RewriteInterceptor.Mapping;
import com.predic8.membrane.core.util.MessageUtil;
public class RegExURLRewriteInterceptorTest {

	private Exchange exc;
	
	@Before
	public void setUp() throws Exception {
		exc = new Exchange();
		exc.setRequest(MessageUtil.getGetRequest("/buy/banana/3"));
	}

	@Test
	public void testRewrite() throws Exception {
		RewriteInterceptor interceptor = new RewriteInterceptor();
		List<Mapping> mappings = new ArrayList<Mapping>();
		mappings.add( new Mapping("/buy/(.*)/(.*)", "/buy?item=$1&amount=$2", null));
		interceptor.setMappings(mappings);

		assertEquals(Outcome.ABORT, interceptor.handleRequest(exc));
		assertEquals("/buy?item=banana&amount=3", exc.getRequest().getUri());
	}
	
}
