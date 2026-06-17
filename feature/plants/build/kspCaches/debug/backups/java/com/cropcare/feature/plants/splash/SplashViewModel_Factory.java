package com.cropcare.feature.plants.splash;

import com.cropcare.core.domain.usecase.IsOnboardingCompletedUseCase;
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
public final class SplashViewModel_Factory implements Factory<SplashViewModel> {
  private final Provider<IsOnboardingCompletedUseCase> isOnboardingCompletedUseCaseProvider;

  private SplashViewModel_Factory(
      Provider<IsOnboardingCompletedUseCase> isOnboardingCompletedUseCaseProvider) {
    this.isOnboardingCompletedUseCaseProvider = isOnboardingCompletedUseCaseProvider;
  }

  @Override
  public SplashViewModel get() {
    return newInstance(isOnboardingCompletedUseCaseProvider.get());
  }

  public static SplashViewModel_Factory create(
      Provider<IsOnboardingCompletedUseCase> isOnboardingCompletedUseCaseProvider) {
    return new SplashViewModel_Factory(isOnboardingCompletedUseCaseProvider);
  }

  public static SplashViewModel newInstance(
      IsOnboardingCompletedUseCase isOnboardingCompletedUseCase) {
    return new SplashViewModel(isOnboardingCompletedUseCase);
  }
}
