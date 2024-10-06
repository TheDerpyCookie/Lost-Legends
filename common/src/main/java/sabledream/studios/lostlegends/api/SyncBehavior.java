package sabledream.studios.lostlegends.api;

public enum SyncBehavior
{


	SYNCABLE(true),
	UNSYNCABLE(false),
	LOCK_WHEN_SYNCED(false);
	private final boolean canSync;

	SyncBehavior(boolean canSync) {
		this.canSync = canSync;
	}

	public boolean canSync() {
		return this.canSync;
	}
}