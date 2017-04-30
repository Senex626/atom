package ru.atom.lecture10.queue;

public class MessageQueue<T> implements BlockingQueue<T> {

	private static final int capacity = 4;
	private int size = 0;
	private static final Object lock = new Object();
	
	public int getCapacity() {
		return size;
	}
	
	public void setCapacity(int capacity) {
		this.size = capacity;
	}
	
	@Override
	public void put(Object elem) throws InterruptedException {
		synchronized (lock) {
			while (true) {
                System.out.println("hello");
                lock.notifyAll();
                try {
                    lock.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
		}
	}

	@Override
	public T take() throws InterruptedException {
		synchronized (lock) {
			
		}
		return null;
	}

	@Override
	public int remainingCapacity() {
		return capacity - size;
	}

	@Override
	public int size() {
		return size;
	}

}
