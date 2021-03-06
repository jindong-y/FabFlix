package edu.uci.ics.jindongy.service.gateway.threadpool;

import edu.uci.ics.jindongy.service.gateway.logger.ServiceLogger;

import java.util.ArrayList;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class ThreadPool
{
    private int numWorkers;

    private ArrayList<Worker> workers;
    private BlockingQueue<ClientRequest> queue;

    /*
     * BlockingQueue is a interface that allows us
     * to choose the type of implementation of the queue.
     * In this case we are using a LinkedBlockingQueue.
     *
     * BlockingQueue as the name implies will block
     * any thread requesting from it if the queue is empty
     * but only if you use the correct function
     */
    private ThreadPool(int numWorkers)
    {
        this.numWorkers = numWorkers;

        workers = new ArrayList<>();
        queue = new LinkedBlockingQueue<>();

        // TODO more work is needed to create the threads
        for(int i=0;i<numWorkers;i++){
            Worker worker=Worker.CreateWorker(i,this);
            worker.start();
            workers.add(worker);
        }

    }

    public static ThreadPool createThreadPool(int numWorkers)
    {
        return new ThreadPool(numWorkers);
    }

    /*
     * Note that this function only has package scoped
     * as it should only be called with the package by
     * a worker
     * 
     * Make sure to use the correct functions that will
     * block a thread if the queue is unavailable or empty
     */
    ClientRequest takeRequest() throws InterruptedException {
        // TODO *take* the request from the queue
        ServiceLogger.LOGGER.info("Take request form queue");

        return queue.take();
    }

    public void putRequest(ClientRequest request) {
        // TODO *put* the request into the queue

        ServiceLogger.LOGGER.info("Put Request into queue");
        try {
            queue.put(request);
        } catch (InterruptedException e) {
            e.printStackTrace();
            ServiceLogger.LOGGER.severe("Put Request into queue Failed!\nInterrupted Exception");
        }
    }

}
