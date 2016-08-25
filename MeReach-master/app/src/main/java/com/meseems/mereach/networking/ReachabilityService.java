package com.meseems.mereach.networking;

import rx.Observable;

/**
 * Created by nickmm on 8/23/16.
 */
public interface ReachabilityService {
    Observable<Boolean> isReachable(String serverUrl);
}
