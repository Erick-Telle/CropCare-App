package com.cropcare.core.data.di;

import com.cropcare.core.data.local.CropCareDatabase;
import com.cropcare.core.data.local.dao.SpeciesDao;
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
public final class DatabaseModule_ProvideSpeciesDaoFactory implements Factory<SpeciesDao> {
  private final Provider<CropCareDatabase> databaseProvider;

  private DatabaseModule_ProvideSpeciesDaoFactory(Provider<CropCareDatabase> databaseProvider) {
    this.databaseProvider = databaseProvider;
  }

  @Override
  public SpeciesDao get() {
    return provideSpeciesDao(databaseProvider.get());
  }

  public static DatabaseModule_ProvideSpeciesDaoFactory create(
      Provider<CropCareDatabase> databaseProvider) {
    return new DatabaseModule_ProvideSpeciesDaoFactory(databaseProvider);
  }

  public static SpeciesDao provideSpeciesDao(CropCareDatabase database) {
    return Preconditions.checkNotNullFromProvides(DatabaseModule.INSTANCE.provideSpeciesDao(database));
  }
}
