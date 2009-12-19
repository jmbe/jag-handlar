import grails.converters.XML
import org.apache.commons.lang.StringUtils
import se.jaghandlar.web.subscribe.JagHandlarSettings

class ContactController {

  def mailService

  JagHandlarSettings jagHandlarSettings

  def support = {

    def fromAddress = StringUtils.isNotEmpty(params.email) ? params.email : jagHandlarSettings.emailFromAddress

    log.info "Sending support mail from '${params.name}', '${params.email}' '${params.organisation}' with message '${params.message}'"

    mailService.sendMail {
      to jagHandlarSettings.supportEmailAddress
      from fromAddress
      subject "Jag handlar support"
      body """\
Meddelande från ${params.name} (${params.organisation ?: ''}):

${params.message ?: '<Tomt meddelande>'}
      """
    }

    render true as XML
  }

}
