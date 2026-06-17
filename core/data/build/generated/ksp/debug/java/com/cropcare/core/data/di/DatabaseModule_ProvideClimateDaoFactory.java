package com.cropcare.core.data.di;

import com.cropcare.core.data.local.CropCareDatabase;
import com.cropcare.core.data.local.dao.ClimateDao;
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
public final class DatabaseModule_ProvideClimateDaoFactory implements Factory<ClimateDao> {
  private final Provider<CropCareDatabase> databaseProvider;

  private DatabaseModule_ProvideClimateDaoFactory(Provider<CropCareDatabase> databaseProvider) {
    this.databaseProvider = databaseProvider;
  }

  @Override
  public ClimateDao get() {
    return provideClimateDao(databaseProvider.get());
  }

  public static DatabaseModule_ProvideClimateDaoFactory create(
      Provider<CropCareDatabase> databaseProvider) {
    return new DatabaseModule_ProvideClimateDaoFactory(databaseProvider);
  }

  public static ClimateDao provideClimateDao(CropCareDatabase database) {
    return Preconditions.checkNotNullFromProvides(DatabaseModule.INSTANCE.provideClimateDao(database));
  }
}
