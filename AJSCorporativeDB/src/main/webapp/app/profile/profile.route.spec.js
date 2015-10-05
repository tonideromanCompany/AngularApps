/* jshint -W117, -W030 */
describe('profile routes', function () {
    describe('state', function () {
        var controller;
        var view = 'app/profile/profile.html';

        beforeEach(function() {
            module('app.profile', bard.fakeToastr);
            bard.inject('$httpBackend', '$location', '$rootScope', '$state', '$templateCache');
        });

        beforeEach(function() {
            $templateCache.put(view, '');
        });

        it('should map state admin to url /profile ', function() {
            expect($state.href('profile', {})).to.equal('/profile');
        });

        it('should map /admin route to profile View template', function () {
            expect($state.get('profile').templateUrl).to.equal(view);
        });

        it('of admin should work with $state.go', function () {
            $state.go('profile');
            $rootScope.$apply();
            expect($state.is('profile'));
        });
    });
});
