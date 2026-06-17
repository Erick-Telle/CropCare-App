package com.cropcare.core.data.di;

import com.cropcare.core.data.local.CropCareDatabase;
import com.cropcare.core.data.local.dao.WateringDao;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import dagger.internal.Provider;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;

@ScopeMetadata
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
public final class DatabaseModule_ProvideWateringDaoFactory implements Factory<WateringDao> {
  private final Provider<CropCareDatabase> databaseProvider;

  private DatabaseModule_ProvideWateringDaoFactory(Provider<CropCareDatabase> databaseProvider) {
    this.databaseProvider = databaseProvider;
  }

  @Override
  public WateringDao get() {
    return provideWateringDao(databaseProvider.get());
  }

  public static DatabaseModule_ProvideWateringDaoFactory create(
      Provider<CropCareDatabase> databaseProvider) {
    return new DatabaseModule_ProvideWateringDaoFactory(databaseProvider);
  }

  public static WateringDao provideWateringDao(CropCareDatabase database) {
    return Preconditions.checkNotNullFromProvides(DatabaseModule.INSTANCE.provideWateringDao(database));
  }
}
