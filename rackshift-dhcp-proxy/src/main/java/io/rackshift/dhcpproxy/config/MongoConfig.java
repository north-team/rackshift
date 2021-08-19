package io.rackshift.dhcpproxy.config;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.MongoDriverInformation;
import com.mongodb.client.MongoClient;
import com.mongodb.client.internal.MongoClientImpl;
import io.rackshift.dhcpproxy.constants.ConfigConstants;
import io.rackshift.dhcpproxy.util.MongoUtil;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.commons.lang.StringUtils;

public class MongoConfig {
    public static void config(PropertiesConfiguration configuration) {
        if (StringUtils.isBlank(configuration.getString(ConfigConstants.MONGOURL))) {
            throw new RuntimeException(ConfigConstants.MONGOURL + " is null! please config first !");
        }
        MongoClientSettings clientSettings = MongoClientSettings.builder().applyConnectionString(new ConnectionString(configuration.getString(ConfigConstants.MONGOURL))).build();
        MongoClient client = new MongoClientImpl(clientSettings, MongoDriverInformation.builder().build());
        MongoUtil.setMongoClient(client);
    }
}
