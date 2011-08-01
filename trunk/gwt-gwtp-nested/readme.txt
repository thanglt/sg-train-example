Using GWTP:
http://code.google.com/p/gwt-platform/wiki/GettingStarted?tm=6

onBind() is called right after the Presenter is constructed. This is the right place to add handlers to the view. If you add a handler, register it by calling registerHandler() so that it is automatically removed later.
onUnbind() is called if the Presenter needs to release its resources. You should usually undo any operations you performed in onBind(), although you don't have to worry about the handlers you registered with registerHandler(), these will be taken care of automatically.
onReveal() is called whenever the Presenter was not visible on screen and becomes visible.
onHide() is called whenever the Presenter was visible on screen and is being hidden.
onReset() is called whenever the user navigates to a page that shows the presenter, whether it was visible or not.

IMPORTANT!
 Whenever you override one of these methods, make sure the first thing you do is call your parent's corresponding method. For example, the first line of your onBind() method should be super.onBind().