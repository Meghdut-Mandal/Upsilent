package com.meghdut.upsilent.models;

public class DownloadProgress
{

    private long downloaded;
    private long uploaded;
    private String info;
    private String elapsedTime;
    private String remainingTime;
    private boolean isComplete;

    public void setDownloaded(long downloaded)
    {
        this.downloaded = downloaded;
    }

    public long getDownloaded()
    {
        return downloaded;
    }

    public void setUploaded(long uploaded)
    {
        this.uploaded = uploaded;
    }

    public long getUploaded()
    {
        return uploaded;
    }

    public void setInfo(String info)
    {
        this.info = info;
    }

    public String getInfo()
    {
        return info;
    }

    public void setElapsedTime(String elapsedTime)
    {
        this.elapsedTime = elapsedTime;
    }

    public String getElapsedTime()
    {
        return elapsedTime;
    }

    public void setRemainingTime(String remainingTime)
    {
        this.remainingTime = remainingTime;
    }

    public String getRemainingTime()
    {
        return remainingTime;
    }

    public void setIsComplete(boolean isComplete)
    {
        this.isComplete = isComplete;
    }

    public boolean getIsComplete()
    {
        return isComplete;
    }

    @Override
    public String toString()
    {
        return "DownloadProgress{" +
                "downloaded=" + downloaded +
                ", uploaded=" + uploaded +
                ", info='" + info + '\'' +
                ", elapsedTime='" + elapsedTime + '\'' +
                ", remainingTime='" + remainingTime + '\'' +
                ", isComplete=" + isComplete +
                '}';
    }
}
