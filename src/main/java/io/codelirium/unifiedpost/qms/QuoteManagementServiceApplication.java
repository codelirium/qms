package io.codelirium.unifiedpost.qms;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;

import static io.codelirium.unifiedpost.qms.QuoteManagementServiceApplication.CORE_PACKAGE;
import static java.lang.Boolean.FALSE;


@SpringBootApplication
@ComponentScan({CORE_PACKAGE})
public class QuoteManagementServiceApplication extends SpringBootServletInitializer {

	public static final String CORE_PACKAGE = "io.codelirium.unifiedpost.qms";


	@Override
	protected SpringApplicationBuilder configure(final SpringApplicationBuilder application) {

		return application.sources(QuoteManagementServiceApplication.class);

	}


	public static void main(final String ... args) {

		new SpringApplicationBuilder(QuoteManagementServiceApplication.class)
				.logStartupInfo(FALSE)
				.run(args);

	}
}
