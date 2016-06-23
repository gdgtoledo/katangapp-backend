package es.craftsmanship.toledo.katangapp.controllers;

import es.craftsmanship.toledo.katangapp.business.Finder;
import es.craftsmanship.toledo.katangapp.business.UnreferenceablePointException;
import es.craftsmanship.toledo.katangapp.business.exception.APIElementNotFoundException;
import es.craftsmanship.toledo.katangapp.business.exception.APIException;
import es.craftsmanship.toledo.katangapp.models.QueryResult;

import com.google.inject.Inject;

import play.libs.F;
import play.libs.Json;

import play.mvc.Result;

/**
 * @author manudevelopia
 * @author mdelapenya
 */
public class KatangappFavoriteApplication extends BaseKatangaApplication {

    @Inject
    public KatangappFavoriteApplication(Finder busStopFinder) {
        this.busStopFinder = busStopFinder;
    }

    public F.Promise<Result> favorite(final String busStopId) {
        try {
            F.Promise<QueryResult> queryResultPromise = 
                busStopFinder.findRoutes(busStopId);

            F.Promise<Result> resultPromise = queryResultPromise.map(
                new F.Function<QueryResult, Result>() {
        
                    @Override
                    public Result apply(QueryResult queryResult)
                        throws Throwable {

                        return prettyPrint(Json.toJson(queryResult));
                    }

                });

            return recover(resultPromise);
        }
        catch (final APIElementNotFoundException ae) {
            return F.Promise.promise(new F.Function0<Result>() {
    
                @Override
                public Result apply() throws Throwable {
                        return notFound(ae.getApiError());
                    }

                });
        }
        catch (InterruptedException | UnreferenceablePointException e) {
            final APIException apiException = new APIException(e.getMessage());

            return F.Promise.promise(new F.Function0<Result>() {
    
                @Override
                public Result apply() throws Throwable {
                    return notFound(apiException.getApiError());
                }

            });
        }

    }

    private Finder busStopFinder;

}
