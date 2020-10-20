# Experimental results

_Briefly (you don't need to write a lot) document the results of your
experiments with throwing a bunch of clients at your server, as described
in the lab write-up. You should probably delete or incorporate this text
into your write-up._

Using `htop`, we were able to see that multiple threads were being created and utilized to
process our requests. When the tests ran, my CPU with 8 cores was being utilized at 100% usage
for all 8 cores for a few seconds until the tests completed.
We ran two tests, one with 10 clients sending the `Threaded-Echo-Client-Server.png` to the
 server, and one with 100 clients doing the same. The first test completed in ~2-3 seconds,
 and the second test took ~15-16 seconds to complete.


_You should indicate here what variations you tried (every connection gets
a new thread, using a threadpool of size X, etc., etc.), and what the
results were like when you spun up a bunch of threads that send
decent-sized files to the server._

Again, we ran two tests. The first test was 10 clients outputting a 95kb png file to the server.
All 8 cores of my CPU seemed to have activity, but it was very brief.
The test completed in ~2-3 seconds.

The second test was 100 clients outputting a 95kb png file to the server.
This test took much longer, around ~15-16 seconds, but clearly showed that the server
was using multiple threads to finish the task.
