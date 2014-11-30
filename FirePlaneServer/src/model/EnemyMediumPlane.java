package model;

import controller.Simulator;
import util.Location;
import util.Speed;

public class EnemyMediumPlane extends EnemyPlane
{

  public EnemyMediumPlane(Location _location, Speed _speed)
  {
    super(_location, _speed);
    HP = 1;
    // TODO Auto-generated constructor stub
  }

  synchronized public void shoot()
  {
    if (isExist())
    {
    SmallBullet bullet = new SmallBullet(
        new Location(this.getLocation()), 
        new Speed(0, 15), 
        this,
        false);
    Simulator.addWeapon(bullet);
    }
  }

  @Override
  synchronized public String getType()
  {
    return "medium";
  }
}
