/* Copyright 2012 predic8 GmbH, www.predic8.com

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

   http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License. */

package com.predic8.membrane.examples.tests.validation;

import static com.predic8.membrane.test.AssertUtils.postAndAssert;
import static org.apache.commons.io.FileUtils.readFileToString;

import java.io.File;
import java.io.IOException;

import org.junit.Test;

import com.predic8.membrane.examples.DistributionExtractingTestcase;
import com.predic8.membrane.examples.Process2;

public class JSONSchemaValidationTest extends DistributionExtractingTestcase {

	@Test
	public void test() throws IOException, InterruptedException {
		File baseDir = getExampleDir("validation" + File.separator + "json-schema");
		Process2 sl = new Process2.Builder().in(baseDir).script("service-proxy").waitForMembrane().start();
		try {
			for (int port : new int[] { 2000, 2001 }) {
				Thread.sleep(1000);
				postAndAssert(200, "http://localhost:" + port + "/", readFileToString(new File(baseDir, "good" + port + ".json")));
				postAndAssert(400, "http://localhost:" + port + "/", readFileToString(new File(baseDir, "bad" + port + ".json")));
			}

		} finally {
			sl.killScript();
		}
	}


}
