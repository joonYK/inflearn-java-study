package optional;

import java.util.Optional;

class OnlineClass {
    private Integer id;

    private String title;

    private boolean closed;

    private Progress progress;

    public OnlineClass(Integer id, String title, boolean closed) {
        this.id = id;
        this.title = title;
        this.closed = closed;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public boolean isClosed() {
        return closed;
    }

    public void setClosed(boolean closed) {
        this.closed = closed;
    }

    public Progress getProgress() {
        /*
         * java8 이전에는 예외를 던지던가 null을 그대로 return하던가 2가지 방법만이 존재.
         * null을 그대로 던지는 경우, 클라이언트쪽에서 null체크를 해줘야 한다. (실수이거나 몰라서 안 할 가능성이 존재)
         * 예외를 던지는 경우에는 checked exception인 경우 클라이언트에 예외 처리를 강제하게 된다.
         * 예외는 stacktrace를 위해 리소스를 사용하게 된다.
         */
        if (this.progress == null)
            throw new IllegalStateException();

        return progress;
    }

    //Optional은 메소드의 리턴 타입으로만 쓰는것이 권장된다.
    public Optional<Progress> getProcess() {
        return Optional.ofNullable(progress);
    }

    public void setProgress(Progress progress) {
        this.progress = progress;
    }

    /*
     * 권장되지 않음.
     * Optional을 메서드의 파라미터로 사용하는것도 가능하지만, 클라이언트에서 setProgress(null)로 null을 보내게 되면 문제가 발생.
     * Optional을 사용하는 의미가 없음.
     */
    public void setProgress(Optional<Progress> progress) {
        progress.ifPresent(p -> this.progress = p);
    }
}
