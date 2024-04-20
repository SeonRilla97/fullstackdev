package seon.full.mallapi.config;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.modelmapper.spi.MatchingStrategy;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static org.modelmapper.config.Configuration.AccessLevel.PRIVATE;

@Configuration
public class RootConfig {
    @Configuration
    public class ModelMapperConfig {
        @Bean
        public ModelMapper modelMapper(){

            ModelMapper modelMapper = new ModelMapper();
            modelMapper
                    .getConfiguration()
                    .setFieldMatchingEnabled(true)
                    .setFieldAccessLevel(PRIVATE)
                    .setMatchingStrategy(MatchingStrategies.LOOSE);
            return modelMapper;

        }
    }
}
