package com.larscheidschmitzhermes.nexus3.github.oauth.plugin.configuration;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.Duration;
import java.util.Properties;

import javax.annotation.PostConstruct;
import javax.inject.Named;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.inject.Singleton;

@Singleton
@Named
public class GithubOauthConfiguration {
    private static final String CONFIG_FILE = "githuboauth.properties";

    private static final String GITHUB_USER_PATH = "/user";

    private static final String GITHUB_ORGS_PATH = "/user/orgs";

    private static final String GITHUB_TEAMS_IN_ORG_PATH = "/teams";

    private static final Duration DEFAULT_PRINCIPAL_CACHE_TTL = Duration.ofMinutes(1);

    private static final String DEFAULT_GITHUB_URL = "https://api.github.com";

    private static final String GITHUB_API_URL_KEY = "github.api.url";

    private static final String GITHUB_PRINCIPAL_CACHE_TTL_KEY = "github.principal.cache.ttl";

    private static final Logger LOGGER = LoggerFactory.getLogger(GithubOauthConfiguration.class);

    private Properties configuration;

    @PostConstruct
    public void init() {
        configuration = new Properties();

        try {
            configuration.load(Files.newInputStream(Paths.get(".", "etc", CONFIG_FILE)));
        } catch (IOException e) {
            LOGGER.error("Error reading github oauth properties", e);
        }
    }

    public String getGithubApiUrl() {
        return configuration.getProperty(GITHUB_API_URL_KEY, DEFAULT_GITHUB_URL);
    }

    public String getGithubTeamsInOrgPath() {
        return GITHUB_TEAMS_IN_ORG_PATH;
    }

    public String getGithubOrgsUri() {
       return getGithubApiUrl() + GITHUB_ORGS_PATH;
    }

    public String getGithubUserUri() {
        return getGithubApiUrl() + GITHUB_USER_PATH;
    }

    public Duration getPrincipalCacheTtl() {
        return Duration.parse(configuration.getProperty(GITHUB_PRINCIPAL_CACHE_TTL_KEY, DEFAULT_PRINCIPAL_CACHE_TTL.toString()));
    }
}
