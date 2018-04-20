package adamatti.sample.view

import adamatti.sample.model.User
import groovy.util.logging.Slf4j
import org.lightadmin.api.config.AdministrationConfiguration
import org.lightadmin.api.config.builder.EntityMetadataConfigurationUnitBuilder
import org.lightadmin.api.config.builder.FieldSetConfigurationUnitBuilder
import org.lightadmin.api.config.builder.ScreenContextConfigurationUnitBuilder
import org.lightadmin.api.config.unit.EntityMetadataConfigurationUnit
import org.lightadmin.api.config.unit.FieldSetConfigurationUnit
import org.lightadmin.api.config.unit.ScreenContextConfigurationUnit

@Slf4j
class UserAdministration extends AdministrationConfiguration<User> {
    UserAdministration(){
        log.info "Created"
    }

    EntityMetadataConfigurationUnit configuration(EntityMetadataConfigurationUnitBuilder configurationBuilder) {
        log.info "Configuration"

        configurationBuilder
            .nameField("firstname")
            .build()
    }

    ScreenContextConfigurationUnit screenContext(ScreenContextConfigurationUnitBuilder screenContextBuilder) {
        log.info "screenContext"

        screenContextBuilder
            .screenName("Users Administration")
            .build()
    }

    FieldSetConfigurationUnit listView(final FieldSetConfigurationUnitBuilder fragmentBuilder) {
        log.info "listView"

        fragmentBuilder
            .field("firstname").caption("First Name")
            .field("lastname").caption("Last Name")
            .build()
    }
}
