/**
 * Copyright 2011 ArcBees Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */

package com.gwtplatform.samples.hplace.client;

import com.gwtplatform.mvp.client.proxy.PlaceManagerImpl;
import com.gwtplatform.mvp.client.proxy.PlaceRequest;
import com.gwtplatform.mvp.client.proxy.TokenFormatter;
import com.gwtplatform.samples.hplace.client.gin.DefaultPlace;

import com.google.gwt.event.shared.EventBus;
import com.google.inject.Inject;

/**
 * @author Christian Goudreau
 */
public class GwtphplacesamplePlaceManager extends PlaceManagerImpl {
  private final PlaceRequest defaultPlaceRequest;

  @Inject
  public GwtphplacesamplePlaceManager(final EventBus eventBus,
      final TokenFormatter tokenFormatter, @DefaultPlace String defaultNameToken) {
    super(eventBus, tokenFormatter);

    this.defaultPlaceRequest = new PlaceRequest(defaultNameToken);
  }

  public void revealDefaultPlace() {
    revealPlace(defaultPlaceRequest);
  }
}