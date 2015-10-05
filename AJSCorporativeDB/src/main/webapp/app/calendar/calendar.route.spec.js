/* jshint -W117, -W030 */
describe('calendar routes', function () {
    describe('state', function () {
        var controller;
        var view = 'app/calendar/calendar.html';

        beforeEach(function() {
            module('app.calendar', bard.fakeToastr);
            bard.inject('$httpBackend', '$location', '$rootScope', '$state', '$templateCache');
        });

        beforeEach(function() {
            $templateCache.put(view, '');
        });

        it('should map state calendar to url /calendar ', function() {
            expect($state.href('calendar', {})).to.equal('/calendar');
        });

        it('should map /calendar route to calendar View template', function () {
            expect($state.get('calendar').templateUrl).to.equal(view);
        });

        it('of calendar should work with $state.go', function () {
            $state.go('calendar');
            $rootScope.$apply();
            expect($state.is('calendar'));
        });
    });
});
