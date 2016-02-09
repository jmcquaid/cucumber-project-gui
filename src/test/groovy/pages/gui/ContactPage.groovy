package pages.gui

import geb.Page

class ContactPage extends Page{
    static url = '/contact'
    static at = {$("body", text : contains("Contact us"))}
}