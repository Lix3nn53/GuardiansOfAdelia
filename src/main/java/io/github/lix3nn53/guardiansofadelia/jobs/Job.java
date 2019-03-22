package io.github.lix3nn53.guardiansofadelia.jobs;

public class Job {

    private JobType jobType;
    private int level;
    private int experience;

    public Job(JobType jobType) {
        this.jobType = jobType;
    }

    public JobType getJobType() {
        return jobType;
    }

    public void setJobType(JobType jobType) {
        this.jobType = jobType;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getExperience() {
        return experience;
    }

    public void setExperience(int experience) {
        this.experience = experience;
    }
}
