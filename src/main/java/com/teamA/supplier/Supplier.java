package com.teamA.supplier;

import com.teamA.data.match.MatchService;
import com.teamA.data.player.PlayerService;
import com.teamA.data.team.TeamService;
import com.teamA.helpers.DateTimer;
import com.teamA.helpers.DateTimerImpl;
import com.teamA.logger.*;
import com.teamA.parsers.JsonParser;
import com.teamA.parsers.JacksonParser;
import com.teamA.serviceFactory.ServiceFactory;
import com.teamA.serviceFactory.ServiceFactoryImpl;
import com.teamA.serviceHelpers.ServiceTransactionHelper;
import com.teamA.serviceHelpers.ServiceTransactionHelperImpl;
import com.teamA.servletHelper.RequestDataRetriver;
import com.teamA.servletHelper.RequestDataRetriverImpl;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class Supplier {

    private static String persistenceUnit = "mundialPU";  // by default postgres
    private static LogPath productionLogPath = LogPath.SYSTEM_LOG;
    private static LogPath testLogPath = LogPath.TEST_LOG;

    private static EntityManagerFactory entityManagerFactory;
    private static EntityManager entityManager;
    private static Logger logger;
    private static ServiceFactory serviceFactory;
    private static ServiceTransactionHelper serviceTransactionHelper;

    private static PlayerService playerService;
    private static TeamService teamService;
    private static MatchService matchService;
    private static JsonParser jsonParser;
    private static RequestDataRetriver dataRetriver;

    public static <T extends PlayerService> PlayerService deliverPlayerService(Class<T> type) {
        if (playerService == null) {
            playerService = deliverServiceFactory().createPlayerService(type);
        }
        return playerService;
    }

    public static <T extends TeamService> TeamService deliverTeamService(Class<T> type) {
        if (teamService == null) {
            teamService = deliverServiceFactory().createTeamService(type);
        }
        return teamService;
    }

    public static <T extends MatchService> MatchService deliverMatchService(Class<T> type) {
        if (matchService == null) {
            matchService = deliverServiceFactory().createMatchService(type);
        }
        return matchService;
    }

    public static EntityManager deliverEntityManager() {
        if (entityManager == null) {
            entityManager = getEntityManagerFactory().createEntityManager();
        }
        return entityManager;
    }

    public static ServiceFactory deliverServiceFactory() {
        if (serviceFactory == null) {

            serviceFactory = ServiceFactoryImpl.create(
                    deliverEntityManager(),
                    getServiceTransactionHelper(),
                    getLogger()
            );
        }
        return serviceFactory;
    }

    public static Logger deliverLogger() {
        if (logger == null) {
            logger = LoggerImpl.getInstance(
                    DateTimerImpl.getInstance(),
                    LogWriterImpl.getInstance(LogPath.SYSTEM_LOG));
        }
        return logger;
    }

    public static JsonParser deliverJsonParser() {
        if (jsonParser == null) {
            jsonParser = JacksonParser.create();
        }
        return jsonParser;
    }

    public static void setPersistenceUnit(PersistenceUnit persistenceUnit) {
        Supplier.persistenceUnit = persistenceUnit.getUnit();
    }

    public static void setLogsPath(LogPath systemLog, LogPath testLog) {
        productionLogPath = systemLog;
        testLogPath = testLog;
    }

    private static EntityManagerFactory getEntityManagerFactory() {

        if (entityManagerFactory == null) {
            entityManagerFactory = Persistence.createEntityManagerFactory(persistenceUnit);
        }
        return entityManagerFactory;
    }

    private static Logger getLogger() {
        if (logger == null) {
            DateTimer dateTimer = DateTimerImpl.getInstance();
            LogWriter logWriter = LogWriterImpl.getInstance(productionLogPath);

            logger = LoggerImpl.getInstance(dateTimer, logWriter);
        }

        return logger;
    }

    private static ServiceTransactionHelper getServiceTransactionHelper() {
        if (serviceTransactionHelper == null) {

            serviceTransactionHelper = ServiceTransactionHelperImpl.create(
                    deliverEntityManager()
            );
        }

        return serviceTransactionHelper;
    }

    public static RequestDataRetriver deliverRequestDataRetriver() {
        if(dataRetriver == null) {
            dataRetriver = new RequestDataRetriverImpl();
        }
        return dataRetriver;
    }
}
