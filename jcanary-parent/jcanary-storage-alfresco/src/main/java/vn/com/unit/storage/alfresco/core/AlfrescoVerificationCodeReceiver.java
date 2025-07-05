/*******************************************************************************
 * Class        AlfrescoVerificationCodeReceiver
 * Created date ：2020/07/27
 * Lasted date  ：2020/07/27
 * Author       ：tantm
 * Change log   ：2020/07/27：01-00 tantm create a new
 ******************************************************************************/
package vn.com.unit.storage.alfresco.core;

/*
 * Copyright (c) 2011 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */

/**
 * AlfrescoVerificationCodeReceiver.
 *
 * @version 01-00
 * @since 01-00
 * @author tantm
 */
public interface AlfrescoVerificationCodeReceiver {

    /**
     * Returns the redirect URI.
     *
     * @return the redirect uri
     * @throws Exception
     *             the exception
     * @author tantm
     */
    String getRedirectUri() throws Exception;

    /**
     * Waits for a verification code.
     *
     * @return the string
     * @author tantm
     */
    String waitForCode();

    /**
     * Releases any resources and stops any processes started.
     *
     * @throws Exception
     *             the exception
     * @author tantm
     */
    void stop() throws Exception;
}
