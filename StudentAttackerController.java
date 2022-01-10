package edu.ufl.cise.cs1.controllers;
import game.controllers.AttackerController;
import game.models.*;
import java.awt.*;
import java.util.List;

public final class StudentAttackerController implements AttackerController
{
	public void init(Game game) { }

	public void shutdown(Game game) { }

	public int update(Game game,long timeDue)
	{
		// declare variables
		int action = -1;

		// for loop: finds the closest defender
		for(Defender defender: game.getDefenders())
		{
			// if defender.isVulnerable() is true
			if(defender.isVulnerable())
			{
				Node defenderLocation = defender.getLocation();
				action = game.getAttacker().getNextDir(defenderLocation, true);
				return action;
			}
		}

		// declare variables
		int distanceToPowerPill = Integer.MAX_VALUE;
		Node closestPowerPill = null;

		// for loop: finds the closest power pill
		for(Node node: game.getPowerPillList())
		{
			// declare variable
			int tempDistanceToPowerPill = game.getAttacker().getLocation().getPathDistance(node);

			// if tempDistanceToPowerPill is less than closestPowerPill
			if(tempDistanceToPowerPill < distanceToPowerPill)
			{
				// then set node to closestPowerPill and set distanceToPowerPill to tempDistanceToPowerPill
				closestPowerPill = node;
				distanceToPowerPill = tempDistanceToPowerPill;
			}
		}

		// if there is a power pill
		// check the distance to that power pill with a value
		// check the distance to the closes defender
		if(closestPowerPill != null)
		{
			// declare variables
			int distanceToDefender = Integer.MAX_VALUE;
			Node closestDefender = null;

			// for loop: finds the closest defender
			for(Defender defender: game.getDefenders())
			{
				// if defender.getLairTime() is less than or equal to 0
				if(defender.getLairTime() <= 0)
				{
					// declare variable
					int tempDistanceToDefender = game.getAttacker().getLocation().getPathDistance(defender.getLocation());

					// if tempDistanceToDefender is less than distanceToDefender
					if(tempDistanceToDefender < distanceToDefender)
					{
						// then set location of defender to closestDefender and set distanceToDefender to tempDistanceToDefender
						closestDefender = defender.getLocation();
						distanceToDefender = tempDistanceToDefender;
					}
				}
			}

			// if distanceToDefender is less than or equal to 7 then, eat pill
			if(distanceToDefender <= 7)
			{
				action = game.getAttacker().getNextDir(closestPowerPill, true);
				return action;
			}
			// else remain still
			else
			{
				// if distanceToDefender is less than or equal to 5 then, stay still
				if(distanceToPowerPill <= 5)
				{
					action = game.getAttacker().getReverse();
					return action;
				}
				// else eat pill
				else
				{
					action = game.getAttacker().getNextDir(closestPowerPill, true);
					return action;
				}
			}
		}

		else
		{
			// declare variables
			int distanceToDefender = Integer.MAX_VALUE;
			Node closestDefender = null;

			// for loop: finds the closest defender
			for(Defender defender: game.getDefenders())
			{
				// if defender.getLairTime() is less than or equal to 0
				if(defender.getLairTime() <= 0)
				{
					// declare variable
					int tempDistanceToDefender = game.getAttacker().getLocation().getPathDistance(defender.getLocation());

					// if tempDistanceToDefender is less than distanceToDefender
					if(tempDistanceToDefender < distanceToDefender)
					{
						// then set location of defender to closestDefender and set distanceToDefender to tempDistanceToDefender
						closestDefender = defender.getLocation();
						distanceToDefender = tempDistanceToDefender;
					}
				}
			}

			// if distanceToDefender is less than or equal to 10, then the other way
			if(distanceToDefender <= 10)
			{
				action = game.getAttacker().getNextDir(closestDefender, false);
				return action;
			}
		}

		// for loop: checks is there are pills
		for(Node node: game.getPillList())
		{
			action = game.getAttacker().getNextDir(node, true);
			return action;
		}

		// if action is equal to -1, then stay still
		if(action == -1)
		{
			action = game.getAttacker().getReverse();
			return action;
		}

		return action;
	}
}