package org.hibernate.ogm.hiking.config;

import org.hibernate.ogm.cfg.Configurable;
import org.hibernate.ogm.cfg.OptionConfigurator;
import org.hibernate.ogm.datastore.mongodb.MongoDB;
import org.hibernate.ogm.datastore.mongodb.options.ReadPreferenceType;
import org.hibernate.ogm.hiking.model.Hike;

/**
 * @author Emmanuel Bernard &lt;emmanuel@hibernate.org&gt;
 */
public class HikeMongoDBOptionConfigurator extends OptionConfigurator {
	@Override
	public void configure(Configurable configurable) {
		configurable.configureOptionsFor( MongoDB.class )
				.entity( Hike.class )
					.readPreference( ReadPreferenceType.NEAREST );
	}
}
