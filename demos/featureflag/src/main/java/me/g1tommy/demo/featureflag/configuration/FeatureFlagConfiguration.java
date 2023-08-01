package me.g1tommy.demo.featureflag.configuration;

import dev.openfeature.contrib.providers.flagd.FlagdProvider;
import dev.openfeature.sdk.Client;
import dev.openfeature.sdk.FeatureProvider;
import dev.openfeature.sdk.OpenFeatureAPI;
import me.g1tommy.demo.featureflag.aop.FeatureFlagAspect;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FeatureFlagConfiguration {

	@Bean
	public FeatureProvider featureProvider() {
		return new FlagdProvider();
	}

	@Bean
	public Client openFeatureClient(FeatureProvider provider) {
		final OpenFeatureAPI instance = OpenFeatureAPI.getInstance();
		instance.setProvider(provider);
		return instance.getClient();
	}

	@Bean
	public FeatureFlagAspect featureFlagAspect(Client openFeatureClient) {
		return new FeatureFlagAspect(openFeatureClient);
	}
}
