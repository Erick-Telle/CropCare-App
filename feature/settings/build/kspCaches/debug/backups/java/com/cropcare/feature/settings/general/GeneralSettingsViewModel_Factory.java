package com.cropcare.feature.settings.general;

import com.cropcare.core.domain.usecase.GetAppUsageSummaryUseCase;
import com.cropcare.feature.settings.data.SettingsPreferencesRepository;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
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
public final class GeneralSettingsViewModel_Factory implements Factory<GeneralSettingsViewModel> {
  private final Provider<SettingsPreferencesRepository> settingsPreferencesRepositoryProvider;

  private final Provider<GetAppUsageSummaryUseCase> getAppUsageSummaryUseCaseProvider;

  private GeneralSettingsViewModel_Factory(
      Provider<SettingsPreferencesRepository> settingsPreferencesRepositoryProvider,
      Provider<GetAppUsageSummaryUseCase> getAppUsageSummaryUseCaseProvider) {
    this.settingsPreferencesRepositoryProvider = settingsPreferencesRepositoryProvider;
    this.getAppUsageSummaryUseCaseProvider = getAppUsageSummaryUseCaseProvider;
  }

  @Override
  public GeneralSettingsViewModel get() {
    return newInstance(settingsPreferencesRepositoryProvider.get(), getAppUsageSummaryUseCaseProvider.get());
  }

  public static GeneralSettingsViewModel_Factory create(
      Provider<SettingsPreferencesRepository> settingsPreferencesRepositoryProvider,
      Provider<GetAppUsageSummaryUseCase> getAppUsageSummaryUseCaseProvider) {
    return new GeneralSettingsViewModel_Factory(settingsPreferencesRepositoryProvider, getAppUsageSummaryUseCaseProvider);
  }

  public static GeneralSettingsViewModel newInstance(
      SettingsPreferencesRepository settingsPreferencesRepository,
      GetAppUsageSummaryUseCase getAppUsageSummaryUseCase) {
    return new GeneralSettingsViewModel(settingsPreferencesRepository, getAppUsageSummaryUseCase);
  }
}
