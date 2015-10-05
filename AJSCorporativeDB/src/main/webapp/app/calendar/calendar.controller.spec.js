/* jshint -W117, -W030 */
describe('CalendarController', function() {
    var controller;
    var events = mockData.getMockEvents();
    beforeEach(function() {
        bard.appModule('app.calendar');
        bard.inject('$controller', '$log', '$rootScope','dataservice');
    });

    beforeEach(function () {
    	sinon.stub(dataservice, 'getEvents').returns($q.when(events));
    	sinon.stub(dataservice, 'getAuth').returns($q.when(auth));
        controller = $controller('CalendarController');
        $rootScope.$apply();
    });

    bard.verifyNoOutstandingHttpRequests();

    describe('Calendar controller', function() {
        it('should be created successfully', function () {
            expect(controller).to.be.defined;
        });

        describe('after activate', function() {
            it('should have title of Admin', function() {
                expect(controller.title).to.equal('Admin');
            });

            it('should have logged "Activated"', function() {
                expect($log.info.logs).to.match(/Activated/);
            });
            it('should have at least 1 event', function () {
                expect(controller.events).to.have.length.above(0);
            });

            it('should have events count of 5', function () {
                expect(controller.events).to.have.length(7);
            });
            
            it('should have at least 1 user authenticated', function () {
                expect(controller.auth).to.have.length.above(0);
            });
        });
    });
});
