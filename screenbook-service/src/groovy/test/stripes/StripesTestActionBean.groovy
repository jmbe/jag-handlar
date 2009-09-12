package test.stripes;


import net.sourceforge.stripes.action.DefaultHandler
import net.sourceforge.stripes.action.ForwardResolution
import net.sourceforge.stripes.action.Resolution
import net.sourceforge.stripes.action.UrlBinding
import net.sourceforge.stripes.integration.spring.SpringBean
import se.internetapplications.web.stripes.action.AbstractActionBean

@UrlBinding("/stripes/test")
class StripesTestActionBean extends AbstractActionBean {

  @SpringBean
  def accountService;

  @DefaultHandler
  public Resolution view() {
    // Make sure dependency injection works.
    System.out.println("account service is ${accountService}.")

    // Return sample file to test forwarding
    return new ForwardResolution("/WEB-INF/sitemesh.xml");
  }
}

