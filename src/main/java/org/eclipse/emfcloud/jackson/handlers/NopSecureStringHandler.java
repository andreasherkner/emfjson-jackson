/********************************************************************************
 * Copyright (c) 2023 EclipseSource and others.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0 which is available at
 * https://www.eclipse.org/legal/epl-2.0, or the MIT License which is
 * available at https://opensource.org/licenses/MIT.
 *
 * SPDX-License-Identifier: EPL-2.0 OR MIT
 ********************************************************************************/
package org.eclipse.emfcloud.jackson.handlers;

import java.io.IOException;
import java.util.Map;

import org.eclipse.emf.ecore.EStructuralFeature;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;

public class NopSecureStringHandler implements SecureStringHandler {

   public static final SecureStringHandler INSTANCE = new NopSecureStringHandler();

   @Override
   public boolean shouldHandle(final EStructuralFeature feature) {
      return false;
   }

   @Override
   public String getReplacement(final EStructuralFeature feature, final Object value) {
      throw new IllegalStateException("This handler does not do anything, this method must not be called!");
   }

   @Override
   public void handleMapValue(final EStructuralFeature feature, final Map<?, ?> value, final JsonGenerator jg,
      final SerializerProvider provider) throws IOException {
      throw new IllegalStateException("This handler does not do anything, this method must not be called!");
   }

}
