package com.meseems.mereach.networking;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;

import rx.Observable;
import rx.Subscriber;

/**
 * Created by nickmm on 8/23/16.
 */
public class ReachabilityServiceSocketImpl implements ReachabilityService {

    public Observable<Boolean> isReachable(final String serverUrl) {

        // Using Observable.create
        // https://github.com/ReactiveX/RxJava/wiki/Creating-Observables
        return Observable.create(new Observable.OnSubscribe<Boolean>() {

            @Override
            public void call(Subscriber<? super Boolean> subscriber) {
                // Method called when subscribed

                // onNext could be called inside an asynchronous method too.
                subscriber.onNext(checkReachability(serverUrl, 1000));
                subscriber.onCompleted();
            }
        });
    }

    private boolean checkReachability(String url, int timeout) {
        // Tip: You could use InetAddress, but is not reliable according to this post:
        // http://stackoverflow.com/questions/9922543/why-does-inetaddress-isreachable-return-false-when-i-can-ping-the-ip-address
        // Using an implementation using sockets
        return checkSocketReachability(url, 80, timeout);
    }

    // Using example in: http://stackoverflow.com/questions/9922543/why-does-inetaddress-isreachable-return-false-when-i-can-ping-the-ip-address
    private static boolean checkSocketReachability(String addr, int openPort, int timeOutMillis) {
        // Any Open port on other machine
        // openPort =  22 - ssh, 80 or 443 - webserver, 25 - mailserver etc.
        try {
            Socket soc = new Socket();
            soc.connect(new InetSocketAddress(addr, openPort), timeOutMillis);
            return true;

        } catch (IOException ex) {
            ex.printStackTrace();
            return false;
        }
    }
}
