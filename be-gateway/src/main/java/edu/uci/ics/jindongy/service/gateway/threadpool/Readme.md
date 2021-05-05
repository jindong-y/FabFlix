ThreadPool:
Manage the threads (works) and request queue.

Worker:
Is a thread in the pool. Is always running.
- Will keep checking the queue for request. 
- If a request is queued, dequeue it and process the request.
- Process() is to send request to the according endpoints 
  and store the response in the response table.
  
