package io.github.lix3nn53.guardiansofadelia.jobs;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class Job {

    private JobType jobType;
    private int level = 1;
    private int experience;

    public Job(JobType jobType) {
        this.jobType = jobType;
    }

    public JobType getJobType() {
        return jobType;
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

    public void addExperience(Player player, int experienceToAdd) {
        this.experience += experienceToAdd;
        player.sendMessage(ChatColor.YELLOW + "Gained " + experienceToAdd + " " + jobType.getName() + ChatColor.YELLOW + " experience");

        int requiredExperienceToLevelUp = getRequiredExperienceToLevelUp();

        if (this.experience >= requiredExperienceToLevelUp) {
            this.experience = this.experience - requiredExperienceToLevelUp;
            this.level++;
            player.sendMessage(ChatColor.GREEN + "Job level up! Your new " + jobType.getName() + ChatColor.GREEN + " level is " + this.level);
        }
    }

    private int getRequiredExperienceToLevelUp() {
        if (level == 2) {
            return 320;
        } else if (level == 3) {
            return 560;
        } else if (level == 4) {
            return 800;
        } else if (level == 5) {
            return 1120;
        } else if (level == 6) {
            return 1440;
        } else if (level == 7) {
            return 1760;
        } else if (level == 8) {
            return 2400;
        }
        return 120;
    }
}
