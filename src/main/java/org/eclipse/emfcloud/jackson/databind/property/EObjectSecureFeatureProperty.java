/*******************************************************************************
 * Copyright (c) 2019-2022 Guillaume Hillairet and others.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0 which is available at
 * https://www.eclipse.org/legal/epl-2.0, or the MIT License which is
 * available at https://opensource.org/licenses/MIT.
 *
 * SPDX-License-Identifier: EPL-2.0 OR MIT
 *******************************************************************************/

package org.eclipse.emfcloud.jackson.databind.property;

import static org.eclipse.emfcloud.jackson.module.EMFModule.Feature.OPTION_SERIALIZE_DEFAULT_VALUE;

import java.io.IOException;
import java.util.Map;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emfcloud.jackson.databind.EMFContext;
import org.eclipse.emfcloud.jackson.handlers.SecureStringHandler;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.SerializerProvider;

public class EObjectSecureFeatureProperty extends EObjectFeatureProperty {

   private final EStructuralFeature feature;
   private final boolean defaultValues;
   private final SecureStringHandler secureStringHandler;

   public EObjectSecureFeatureProperty(final EStructuralFeature feature, final JavaType type, final int features,
      final SecureStringHandler secureStringHandler) {
      super(feature, type, features);

      this.feature = feature;
      this.secureStringHandler = secureStringHandler;
      this.defaultValues = OPTION_SERIALIZE_DEFAULT_VALUE.enabledIn(features);

   }

   @Override
   public void serialize(final EObject bean, final JsonGenerator jg, final SerializerProvider provider)
      throws IOException {

      EMFContext.setParent(provider, bean);
      EMFContext.setFeature(provider, feature);

      if (bean.eIsSet(feature)) {
         Object value = bean.eGet(feature, false);
         writeValue(jg, provider, value);
      } else if (defaultValues) {
         Object value = feature.getDefaultValue();
         writeValue(jg, provider, value);
      }
   }

   private void writeValue(final JsonGenerator jg, final SerializerProvider provider, final Object value)
      throws IOException {
      jg.writeFieldName(getFieldName());
      if (value != null) {
         if (value instanceof Map) {
            secureStringHandler.handleMapValue(feature, (Map<?, ?>) value, jg, provider);
         } else {
            jg.writeString(secureStringHandler.getReplacement(feature, value));
         }
      } else {
         jg.writeNull();
      }
   }

}
