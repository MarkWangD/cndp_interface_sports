package com.decathlon.cndp_interface_dev.configure;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.decathlon.cndp_interface_dev.utils.HttpRequestUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

@Configuration
//@MapperScan(basePackages = "com.decathlon.bi.mapper.primary", sqlSessionFactoryRef = "primarySqlSessionFactory")
//@MapperScan(basePackages = "com.decathlon.cndp_interface_dev.SportMapper", sqlSessionFactoryRef = "primarySqlSessionFactory")
@Slf4j
public class DruidConfigPrimary {

	static final String MAPPER_LOCATION = "classpath:mybatis/mapper/primary/*.xml";
	
	private static String VAULT_ROLE_ID = null;
	private static String VAULT_SECRET_ID = null;
	private static String VAULT_PATH = null;

	public static String getTokenUrl = "https://vault-china.dktapp.cloud/v1/auth/approle/login";

	public static String postgresql_sa_cndp_for_poslog_host;
	public static String postgresql_sa_cndp_for_poslog_password;
	public static String postgresql_sa_cndp_for_poslog_username;

	static {
		Properties properties = new Properties();
//		 使用ClassLoader加载properties配置文件生成对应的输入流
		InputStream in = DruidConfigPrimary.class.getClassLoader().getResourceAsStream("application.properties");
		// 使用properties对象加载输入流
		try {
			properties.load(in);
		} catch (IOException e) {
			e.printStackTrace();
		}

		// 获取系统环境变量
		VAULT_ROLE_ID = System.getenv("VAULT_ROLE_ID");
		VAULT_SECRET_ID = System.getenv("VAULT_SECRET_ID");

	//	VAULT_ROLE_ID = "a4d8cbc5-2887-42df-6e1b-7fd1698124ab";
	//	VAULT_SECRET_ID = " 89815a7f-181c-a316-8d28-461d4e26861c";



		// 获取配置文件变量
		VAULT_PATH = properties.getProperty("VAULT_PATH");

		log.info("VAULT_PATH ------------->> " + VAULT_PATH);
		log.info("VAULT_ROLE_ID ------------->> " + VAULT_ROLE_ID);

		// 获取vault 变量
		try {
			StringBuilder sb = new StringBuilder();
			sb.append("{\"role_id\":\"");
			sb.append(VAULT_ROLE_ID);
			sb.append("\",\"secret_id\":\"");
			sb.append(VAULT_SECRET_ID);
			sb.append("\"}");

			String loginTokenJson = HttpRequestUtils.httpPost(getTokenUrl, sb.toString(), null, false);
			JSONObject subParseObject = JSON.parseObject(loginTokenJson);
			System.out.println("subParseObject =="+ subParseObject);
			String client_token = subParseObject.getJSONObject("auth").getString("client_token");

			System.out.println(client_token);
			Map<String, String> headers = new HashMap<String, String>();
			headers.put("X-Vault-Token", client_token);

			String getVaultUrl = "https://vault-china.dktapp.cloud/v1/secret/data/" + VAULT_PATH + "/postgresql";
			String getValueJson = HttpRequestUtils.get(getVaultUrl, false, headers);
			subParseObject = JSON.parseObject(getValueJson);
			JSONObject jsonObject = subParseObject.getJSONObject("data").getJSONObject("data");

			postgresql_sa_cndp_for_poslog_host = jsonObject.getString("postgresql_sa_cndp_for_poslog_host");
			postgresql_sa_cndp_for_poslog_password = jsonObject.getString("postgresql_sa_cndp_for_poslog_password");
			postgresql_sa_cndp_for_poslog_username = jsonObject.getString("postgresql_sa_cndp_for_poslog_username");
			
//			postgresql_sa_cndp_for_poslog_host = "jdbc:postgresql://10.50.8.227:60906/dw";
//			postgresql_sa_cndp_for_poslog_username = "service_account_java_etl_job";
//			postgresql_sa_cndp_for_poslog_password = "P!*VI*fce@t2E8LK";

			log.info("postgresql_sa_cndp_for_poslog_host -->> " + postgresql_sa_cndp_for_poslog_host);
			log.info("postgresql_sa_cndp_for_poslog_username -->> " + postgresql_sa_cndp_for_poslog_username);
			log.info("postgresql_sa_cndp_for_poslog_password -->> " + postgresql_sa_cndp_for_poslog_password);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Value("${spring.datasource.driver-class-name}")
	private String driverClassName;

	@Value("${spring.datasource.initialSize}")
	private int initialSize;

	@Value("${spring.datasource.minIdle}")
	private int minIdle;

	@Value("${spring.datasource.maxActive}")
	private int maxActive;

	@Value("${spring.datasource.maxWait}")
	private int maxWait;

	@Value("${spring.datasource.timeBetweenEvictionRunsMillis}")
	private int timeBetweenEvictionRunsMillis;

	@Value("${spring.datasource.minEvictableIdleTimeMillis}")
	private int minEvictableIdleTimeMillis;

	@Value("${spring.datasource.validationQuery}")
	private String validationQuery;

	@Value("${spring.datasource.testWhileIdle}")
	private boolean testWhileIdle;

	@Value("${spring.datasource.testOnBorrow}")
	private boolean testOnBorrow;

	@Value("${spring.datasource.testOnReturn}")
	private boolean testOnReturn;

	@Value("${spring.datasource.filters}")
	private String filters;

	@Value("${spring.datasource.logSlowSql}")
	private String logSlowSql;

	@Bean(name = "primaryDataSource")
	@Primary
	public DataSource primaryDataSource() {
		DruidDataSource datasource = new DruidDataSource();
		datasource.setUrl(postgresql_sa_cndp_for_poslog_host);
		log.info("postgresql_sa_cndp_for_poslog_host -->> " + postgresql_sa_cndp_for_poslog_host);
		datasource.setUsername(postgresql_sa_cndp_for_poslog_username);
		datasource.setPassword(postgresql_sa_cndp_for_poslog_password);


//		datasource.setUrl("jdbc:postgresql://dmt-pgs-prd.pg.rds.aliyuncs.com:1921/dw");
//		datasource.setUsername("service_account_talend_dataplatform");
//		datasource.setPassword("a32BjzD2gJEtGZxW");
		datasource.setDriverClassName(driverClassName);
		datasource.setInitialSize(initialSize);
		datasource.setMinIdle(minIdle);
		datasource.setMaxActive(maxActive);
		datasource.setMaxWait(maxWait);
		datasource.setTimeBetweenEvictionRunsMillis(timeBetweenEvictionRunsMillis);
		datasource.setMinEvictableIdleTimeMillis(minEvictableIdleTimeMillis);
		datasource.setValidationQuery(validationQuery);
		datasource.setTestWhileIdle(testWhileIdle);
		datasource.setTestOnBorrow(testOnBorrow);
		datasource.setTestOnReturn(testOnReturn);
		datasource.setQueryTimeout(60000);
//		try {
//			datasource.setFilters(filters);
//		} catch (SQLException e) {
//			log.error("druid configuration initialization filter", e);
//		}
		return datasource;
	}

	@Bean(name = "primaryTransactionManager")
	@Primary
	public DataSourceTransactionManager primaryTransactionManager(
			@Qualifier("primaryDataSource") DataSource dataSource) {
		return new DataSourceTransactionManager(dataSource);
	}

//	@Bean(name = "primarySqlSessionFactory")
//	@Primary
//	public SqlSessionFactory primarySqlSessionFactory(@Qualifier("primaryDataSource") DataSource primaryDataSource)
//			throws Exception {
//		final SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
//		sessionFactory.setDataSource(primaryDataSource);
//		sessionFactory.setMapperLocations(
//				new PathMatchingResourcePatternResolver().getResources(DruidConfigPrimary.MAPPER_LOCATION));
//		Properties properties = new Properties();
//		properties.setProperty("mappers", "com.decathlon.cndp_interface_dev.mapper");// 通用DAO
//		properties.setProperty("notEmpty", "false");
//		properties.setProperty("ORDER", "BEFORE");
//		sessionFactory.setConfigurationProperties(properties);
//		return sessionFactory.getObject();
//	}

}
