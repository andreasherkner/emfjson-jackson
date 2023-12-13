package org.eclipse.emfcloud.jackson.handlers;

import java.io.IOException;
import java.util.Map;

import org.eclipse.emf.ecore.EStructuralFeature;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;

public interface SecureStringHandler {

   boolean shouldHandle(EStructuralFeature feature);

   String getReplacement(EStructuralFeature feature, Object value);

   void handleMapValue(EStructuralFeature feature, Map<?, ?> value, final JsonGenerator jg,
      final SerializerProvider provider) throws IOException;
}
