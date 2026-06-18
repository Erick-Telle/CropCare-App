package com.cropcare.feature.watering.event;

import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;

@ScopeMetadata("javax.inject.Singleton")
@QualifierMetadata
@DaggerGenerated
@Generated(
    value = "dagger.internal.codegen.ComponentProcessor",
    comments = "https://dagger.dev"
)
@SuppressWarnings({
    "unchecked",
    "rawtypes",
    "KotlinInternal",
    "KotlinInternalInJava",
    "cast",
    "deprecation",
    "nullness:initialization.field.uninitialized"
})
public final class WateringEventEmitterImpl_Factory implements Factory<WateringEventEmitterImpl> {
  @Override
  public WateringEventEmitterImpl get() {
    return newInstance();
  }

  public static WateringEventEmitterImpl_Factory create() {
    return InstanceHolder.INSTANCE;
  }

  public static WateringEventEmitterImpl newInstance() {
    return new WateringEventEmitterImpl();
  }

  private static final class InstanceHolder {
    static final WateringEventEmitterImpl_Factory INSTANCE = new WateringEventEmitterImpl_Factory();
  }
}
