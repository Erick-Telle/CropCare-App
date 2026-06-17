package com.cropcare.feature.plants.onboarding;

import com.cropcare.core.domain.usecase.CompleteOnboardingUseCase;
import com.cropcare.core.domain.usecase.SaveClimateConfigUseCase;
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
public final class OnboardingViewModel_Factory implements Factory<OnboardingViewModel> {
  private final Provider<SaveClimateConfigUseCase> saveClimateConfigUseCaseProvider;

  private final Provider<CompleteOnboardingUseCase> completeOnboardingUseCaseProvider;

  private OnboardingViewModel_Factory(
      Provider<SaveClimateConfigUseCase> saveClimateConfigUseCaseProvider,
      Provider<CompleteOnboardingUseCase> completeOnboardingUseCaseProvider) {
    this.saveClimateConfigUseCaseProvider = saveClimateConfigUseCaseProvider;
    this.completeOnboardingUseCaseProvider = completeOnboardingUseCaseProvider;
  }

  @Override
  public OnboardingViewModel get() {
    return newInstance(saveClimateConfigUseCaseProvider.get(), completeOnboardingUseCaseProvider.get());
  }

  public static OnboardingViewModel_Factory create(
      Provider<SaveClimateConfigUseCase> saveClimateConfigUseCaseProvider,
      Provider<CompleteOnboardingUseCase> completeOnboardingUseCaseProvider) {
    return new OnboardingViewModel_Factory(saveClimateConfigUseCaseProvider, completeOnboardingUseCaseProvider);
  }

  public static OnboardingViewModel newInstance(SaveClimateConfigUseCase saveClimateConfigUseCase,
      CompleteOnboardingUseCase completeOnboardingUseCase) {
    return new OnboardingViewModel(saveClimateConfigUseCase, completeOnboardingUseCase);
  }
}
